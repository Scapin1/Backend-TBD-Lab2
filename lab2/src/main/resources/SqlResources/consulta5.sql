WITH daily_sales AS (
    SELECT
        DATE_TRUNC('day', date_transaction) AS date,
        EXTRACT(MONTH FROM date_transaction) AS month,
        EXTRACT(YEAR FROM date_transaction) AS year,
        SUM(amount_product) AS total_sales_day
    FROM Transactions
    WHERE type_transaction = 'Sale'
      AND date_transaction >= CURRENT_DATE - INTERVAL '1 year'
    GROUP BY date, month, year
),
monthly_sales AS (
    SELECT
        year,
        month,
        SUM(total_sales_day) AS total_sales_month,
        /* Calcula la cantidad de días del mes usando DATE_TRUNC + INTERVAL*/
        EXTRACT(DAY FROM DATE_TRUNC('month', MAKE_DATE(year::int, month::int, 1)) + INTERVAL '1 month - 1 day') AS days_in_month
    FROM daily_sales
    GROUP BY year, month
),
sales_with_difference AS (
    SELECT
        TO_CHAR(TO_DATE(month::text, 'MM'), 'Month') AS month_name,
		month,
        year,
        ROUND(total_sales_month / days_in_month, 2) AS average_daily_sales,
		/*
		.-LAG es la funcion ventana que toma el valor de la fila anterior, con un ROUND a 2 decimales
		.-OVER define el orden cronologico
		*/
        LAG(ROUND(total_sales_month / days_in_month, 2)) OVER (ORDER BY year, month) AS previous_month_avg,
		/*
		Calculo de deferencia en los promedios
		*/
        ROUND((total_sales_month / days_in_month) - LAG(total_sales_month / days_in_month) OVER (ORDER BY year, month), 2) AS difference
    FROM monthly_sales
)
SELECT
    month_name,
    year,
    average_daily_sales,
    difference AS difference_from_previous_month
FROM sales_with_difference
ORDER BY year, month;
