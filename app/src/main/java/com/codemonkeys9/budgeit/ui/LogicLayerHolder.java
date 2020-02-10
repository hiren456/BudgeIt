package com.codemonkeys9.budgeit.ui;

import com.codemonkeys9.budgeit.logiclayer.LogicLayer;
import com.codemonkeys9.budgeit.logiclayer.LogicLayerFactory;

final class LogicLayerHolder {
    private static LogicLayer logicLayer;
    public static LogicLayer getLogicLayer() {
        return logicLayer;
    }
    public static void init() {
        if(logicLayer == null) {
            logicLayer = LogicLayerFactory.createLogicLayer();
        }
    }
}
