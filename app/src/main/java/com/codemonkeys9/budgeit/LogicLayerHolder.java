package com.codemonkeys9.budgeit;

import com.codemonkeys9.budgeit.LogicLayer.LogicLayer;
import com.codemonkeys9.budgeit.LogicLayer.LogicLayerFactory;

final class LogicLayerHolder {
    private static LogicLayer logicLayer;
    public static LogicLayer getLogicLayer() {
        return logicLayer;
    }
    public static void init() {
        if(logicLayer == null) {
            logicLayer = new LogicLayerFactory().createLogicLayer();
        }
    }
}
