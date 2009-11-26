/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package values;

/**
 *
 * @author Rémi
 */
public interface IValues
{
	public static final int infinity = Integer.MAX_VALUE / 10;
    public boolean equals(IValues ivalue);
    public AbstractValues get();
}
