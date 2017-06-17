package util;

import java.util.List;

public class PageBean
{
    private int curPage;
    private int totalPages;
    private int totalRows;
    private int pageSize;
    private List data;

    public int getCurPage()
    {
        if (curPage > getTotalPages())
            curPage = getTotalPages();
        else if (curPage < 1)
            curPage = 1;
        return curPage;
    }

    public void setCurPage(int curPage)
    {
        this.curPage = curPage;
    }

    public int getTotalPages()
    {
        if (totalRows > 0 && totalRows % pageSize == 0)
            totalPages = totalRows / pageSize;
        else
            totalPages = totalRows / pageSize + 1;
        return totalPages;
    }

    public void setTotalPages(int totalPages)
    {
        this.totalPages = totalPages;
    }

    public int getTotalRows()
    {
        return totalRows;
    }

    public void setTotalRows(int totalRows)
    {
        this.totalRows = totalRows;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public List getData()
    {
        return data;
    }

    public void setData(List data)
    {
        this.data = data;
    }
}
