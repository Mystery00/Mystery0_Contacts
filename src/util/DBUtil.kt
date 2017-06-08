package util

import java.sql.*
import java.util.ArrayList
import java.util.HashMap

class DBUtil
{
    private val USERNAME = "root"
    private val PASSWORD = "root"
    private val DRIVER = "com.mysql.jdbc.Driver"
    private val URL = "jdbc:mysql://localhost:3306/"
    private var databaseName: String? = null
    private var connection: Connection? = null
    private var preparedStatement: PreparedStatement? = null
    private var resultSet: ResultSet? = null
    private var pageSize:Int=3

    constructor(databaseName: String)
    {
        try
        {
            Class.forName(DRIVER)
            this.databaseName = this.databaseName
        }
        catch (e: ClassNotFoundException)
        {
            e.printStackTrace()
        }
    }

    fun getConnection(): Connection?
    {
        try
        {
            connection = DriverManager.getConnection(URL + databaseName, USERNAME, PASSWORD)
        }
        catch (e: SQLException)
        {
            e.printStackTrace()
        }
        return connection
    }

    fun getPreparedStatement(sql: String): PreparedStatement?
    {
        try
        {
            preparedStatement = getConnection()!!.prepareStatement(sql)
        }
        catch (e: SQLException)
        {
            e.printStackTrace()
        }
        return preparedStatement
    }

    private fun setParams(sql: String, params: Array<String>?)
    {
        try
        {
            preparedStatement = getPreparedStatement(sql)
            if (params != null)
            {
                for (i in params.indices)
                {
                    preparedStatement!!.setString(i + 1, params[i])
                }
            }
        }
        catch (e: SQLException)
        {
            e.printStackTrace()
        }

    }

    fun getList(sql: String, params: Array<String>): List<*>
    {
        val list = ArrayList<Any>()
        try
        {
            setParams(sql, params)
            val resultSet = preparedStatement!!.executeQuery()
            val resultSetMetaData = resultSet.metaData
            while (resultSet.next())
            {
                val map = HashMap<Any,Any>()
                (0..resultSetMetaData.columnCount - 1)
                        .asSequence()
                        .map { resultSetMetaData.getCatalogName(it) }
                        .forEach { map.put(it, resultSet.getString(it)) }
                list.add(map)
            }
            resultSet.close()
        }
        catch (e: SQLException)
        {
            e.printStackTrace()
        }

        return list
    }

    fun getMap(sql: String, params: Array<String>): Map<*, *>?
    {
        val list = getList(sql, params)
        if (list.isEmpty())
            return null
        return list[0] as Map<*, *>
    }

    fun update(sql: String, params: Array<String>): Int
    {
        var recNo = -1
        try
        {
            setParams(sql, params)
            recNo = preparedStatement!!.executeUpdate()
        }
        catch (e: SQLException)
        {
            e.printStackTrace()
        }

        return recNo
    }

    private fun getTotalRows(tempSql: String, params: Array<String>): Int
    {
        var sql = tempSql
        val totalRows: Int
        sql = sql.toLowerCase()
        val countSql: String
        if (sql.contains("group"))
        {
            countSql = "select count(*) from ($sql) as tempNum"
        }
        else
        {
            countSql = "select count(*) as tempNum " + sql.substring(sql.indexOf("from"))
        }
        val count = getMap(countSql, params)!!["tempNum"] as String
        totalRows = Integer.parseInt(count)
        return totalRows
    }

    fun getPageBean(sql: String, params: Array<String>, curPage: Int): PageBean
    {
        val newSql = sql + " limit " + (curPage - 1)*pageSize + "," + pageSize
        val data = getList(newSql, params)
        val pageBean = PageBean()
        pageBean.curPage = curPage
        pageBean.pageSize = pageSize
        pageBean.totalRows = getTotalRows(sql, params)
        pageBean.data = data
        return pageBean
    }
}