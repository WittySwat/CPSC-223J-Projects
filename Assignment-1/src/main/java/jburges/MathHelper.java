/*
 *  Program Name: "Payroll System".  This program shows how to add and subtract two numbers using a simple UI with three
 *  active buttons.  Copyright (C) 2021 Jarrod Burges
 *  This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
 *  version 3 as published by the Free Software Foundation.
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 *  warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *  A copy of the GNU General Public License v3 is available here:  <https://www.gnu.org/licenses/>.
 */

package jburges;
/**
 * Represents mathematical computational methods to be used within the Application.
 * @author Jarrod Burges
 * @email jburges@csu.fullerton.edu
 */
public final class MathHelper {
    /**
     * Calculates regularPay which is hoursWorked * hourlyPay but up to hoursWorked <= 40.0.
     * Values of hoursWorked > 40.0 return hourlyPay * 40.0
     *
     * @param hoursWorked amount of hours worked by an employee
     * @param hourlyPay hourly pay for an employee
     * @return regularPay pay for hours worked less than or equal to 40.0
     */
    public static double getRegularPay (double hoursWorked, double hourlyPay) {
        if (hoursWorked > 40.0) {
            return 40.0 * hourlyPay;
        }
        else {
            return hoursWorked * hourlyPay;
        }
    }

    /**
     * Calculates overtimePay, time and a half, which is 0.0 if hoursWorked <= 40.0. Otherwise calculates overtimePay as
     * (hoursWorked - 40) * (1.5 * hourlyPay).
     *
     * @param hoursWorked amount of hours worked by an employee
     * @param hourlyPay hourly pay for an employee
     * @return overtimePay pay for hours worked greater than 40.0
     */
    public static double getOvertimePay (double hoursWorked, double hourlyPay) {
        hoursWorked-=40.0;
        if (hoursWorked >= 0.0) {
            return hoursWorked * (1.5 * hourlyPay);
        }
        else {
            return 0.0;
        }
    }

    /**
     * Returns the addition of regularPay and overtimePay.
     *
     * @param regularPay pay for worked hours 40.0 and less
     * @param overtimePay pay for worked hours greater than 40.0 hours
     * @return grossPay regularPay and overtimePay added
     */
    public static double getGrossPay (double regularPay, double overtimePay) {
        return regularPay + overtimePay;
    }
}
