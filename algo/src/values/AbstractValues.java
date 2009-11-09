/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package values;

/**
 * représente des valeurs d'arcs abstraites
 * @author Rémi
 */
public abstract class AbstractValues implements IValues
{

    /**
     * représente l'abscence de valeur
     * @return l'objet représentant l'abscence de valeur
     */
    public static AbstractValues noValue()
    {
        return null;
    }

    public abstract boolean equals(IValues ivalues);
}
