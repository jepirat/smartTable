/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.smart.table.project;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.serial.SerialParameters;
import com.intelligt.modbus.jlibmodbus.serial.SerialPort;
import com.intelligt.modbus.jlibmodbus.serial.SerialPortException;
import com.intelligt.modbus.jlibmodbus.serial.SerialPortFactoryJSSC;
import com.intelligt.modbus.jlibmodbus.serial.SerialUtils;
import jssc.SerialPortList;

/**
 *
 * @author jeka
 */
public class Controller {
    boolean turbine, lamp;
    SerialParameters serialParameters = new SerialParameters();
    Properties properties = new Properties();
    ModbusMaster modbusMaster;

    public Controller() throws SerialPortException {
        serialParameters.setDevice(properties.getProperty("port"));
        serialParameters.setBaudRate(SerialPort.BaudRate.BAUD_RATE_9600);
        serialParameters.setDataBits(8);
        serialParameters.setParity(SerialPort.Parity.NONE);
        serialParameters.setStopBits(1);
        SerialUtils.setSerialPortFactory(new SerialPortFactoryJSSC());
        ModbusMasterFactory.createModbusMasterRTU(serialParameters);
    }
    
    
    public String[] getComPorts() { 
        return SerialPortList.getPortNames();  
    }  
    
    public void turbineOnOf() throws ModbusIOException, ModbusProtocolException, ModbusNumberException {
        modbusMaster.connect();
        if (turbine == false) {
            turbine = true;
            modbusMaster.writeSingleCoil(2, 0, true);              
        } if (turbine == true) {
           turbine = false;
            modbusMaster.writeSingleCoil(2, 0, false); 
        }
    }
    
    public void lampeOnOf() throws ModbusIOException, ModbusProtocolException, ModbusNumberException {
        modbusMaster.connect();
        if (lamp == false) {
            lamp = true;
            modbusMaster.writeSingleCoil(2, 1, true);              
        } if (lamp == true) {
           lamp = false;
           modbusMaster.writeSingleCoil(2, 1, false); 
        }
    }
}
