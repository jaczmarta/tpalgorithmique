/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import java.util.List;

/**
 * représente la valeur d'un arc dans un graphe orienté valué
 * @author Rémi
 */
public class IntValues extends AbstractValues
{

    private int value;

    /**
     * constructeur par paramètre
     * @param cost
     */
    public IntValues(int cost)
    {
        setValue(cost);
    }

    /**
     * @return the value
     */
    public int getValue()
    {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int cost)
    {
        this.value = cost;
    }

    /**
     * abscence de cout
     * @return un cout à -1
     */
    public static IntValues noValue()
    {
        return new IntValues(Integer.MAX_VALUE / 10);
    }

    @Override
    public String toString()
    {
        if (equals(noValue())) {
            return "-";
        } else {
            return getValue() + "";
        }
    }

    /**
     * equals
     * @param intValues paramètre à comparer
     * @return vrai si les valeurs sont égales, faux sinon ou si le paramètre est null
     */
    public boolean equals(IntValues intValues)
    {
        if (intValues == null)
        {
            return false;
        }
        return getValue() == intValues.getValue();
    }
}
