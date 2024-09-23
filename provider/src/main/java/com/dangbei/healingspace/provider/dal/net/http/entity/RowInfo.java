package com.dangbei.healingspace.provider.dal.net.http.entity;


import com.dangbei.healingspace.provider.dal.net.http.IHealingSpaceRowType;
import com.dangbei.healingspace.provider.dal.net.http.entity.column.ColumnInfo1;

import java.util.List;

public class RowInfo {
    public String title;
    public List<ColumnInfo1> columnInfos;
    public int rowHeight;

    private @IHealingSpaceRowType String type;

    public Integer getHeight() {
        return rowHeight;
    }

    public void setHeight(int rowHeight) {
        this.rowHeight = rowHeight;
    }


    public String getTitle() {
        return title;
    }

    public List<ColumnInfo1> getColumnInfos() {
        return columnInfos;
    }

    public @IHealingSpaceRowType String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
