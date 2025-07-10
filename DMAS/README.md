# Data Management & Analytics Projects 📊

This repository contains two SQL-based academic projects developed as part of the course:  
**"Data Management and Analytics Systems"**

---

## 📁 Contents

### 📘 Project 1 – Indexing Optimization in SQL Server
- Creation and evaluation of **non-clustered indexes** on large tables (`orders`, `lineitem`, `parts`, etc.)
- Query tuning using:
  - Execution plans comparison (with/without indexes)
  - Logical/physical/read-ahead reads monitoring
- Use of **composite indexes** and **INCLUDE columns**
- Cost-based query transformation using **JOIN** reordering and **UNION** vs **INTERSECT**


---

### 📗 Project 2 – Data Warehouse & OLAP Queries
- Construction of a star schema with:
  - One **fact table** (`transactions`)
  - Five **dimension tables** (`owners`, `cards`, `cities`, `tdate`, `transaction_type`)
- **ETL logic** using `INSERT INTO ... SELECT` from raw data (`CardsTransactions`)
- OLAP-style queries using:
  - `ROLLUP`, `CUBE`, `GROUP BY`
  - Views with `SCHEMABINDING`
  - **Clustered indexes** on views for performance
- Analysis of transactions by gender, card brand, city, and time
