package values;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

/**
 * représente la valeur d'un arc dans un graphe orienté valué
 */
public class IntValues extends AbstractValues implements Serializable, Externalizable
{

    private int value;

    public IntValues()
    {
        value = IValues.infinity;
    }
    
    /**
     * constructeur par paramètre
     * @param cost
     */
    public IntValues(int cost)
    {
        value = cost;
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
        return new IntValues(IValues.infinity);
    }

    @Override
    public String toString()
    {
        if (equals(noValue()))
        {
            return "-";
        } else
        {
            return getValue() + "";
        }
    }

    /**
     * equals
     * @param intValues paramètre à comparer
     * @return vrai si les valeurs sont égales, faux sinon ou si le paramètre est null
     */
    public boolean equals(IValues intValues)
    {
        if (intValues == null)
        {
            return false;
        }
        return (getValue() == ((IntValues) intValues).getValue());
    }
    
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		this.value = in.readInt();
	}

	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(value);
		
	}

	@Override
	public AbstractValues get() {
		return new IntValues(value);
	}
}