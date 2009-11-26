package graphs;

import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import values.AbstractValues;
import values.IValues;

/**
 * représente un graphe quelconque (valué ou non, arcs labellisés par des objets Type)
 * @author remi
 */
public abstract class AbstractGraph<Type extends AbstractValues> implements Serializable, Externalizable
{

    protected IValues[][] values; //matrice des labels des arcs
    protected static int DEFAULT_NB_VERTICES = 1000; //Nombre par défaut de sommets

    /**
     * désigne l'abscence de valeur d'un arc
     * @return l'objet représentant une abscence de valeur
     */
    public abstract IValues noValue();

    /**
     * Constructeur sans paramètre
     */
    public AbstractGraph()
    {
        this(DEFAULT_NB_VERTICES);
    }

    /**
     * constructeur avec spécification de la taille du graphe
     * @param size taille du graphe
     */
    public AbstractGraph(int size)
    {
        values = (Type[][]) new AbstractValues[size][size];
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                values[i][j] = noValue();
            }
        }
    }

    /**
     * Constructeur par copie
     * @param g
     */
    public AbstractGraph(AbstractGraph g)
    {
        values = (Type[][]) new AbstractValues[g.size()][g.size()];
        for (int i = 0; i < g.size(); i++)
        {
            for (int j = 0; j < g.size(); j++)
            {
                values[i][j] = g.values[i][j].get();
            }
        }
    }

    /**
     * Constructeur par valeur;
     * @param adj la matrice d'adjacence du graphe (matrice des labels des arcs)
     */
    public AbstractGraph(Type[][] values)
    {

        //copie en profondeur
        int size = values.length;
        this.values = (Type[][]) new AbstractValues[size][size];

        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                this.values[i][j] = values[i][j];
            }
        }
    }

    /**
     * retourne le label de l'arc (i, j)
     * @param i sommet de départ
     * @param j sommet d'arrivée
     * @return le label de l'arc (i, j)
     */
    public IValues get(int i, int j)
    {
        try
        {
            checkIndex(i);
            checkIndex(j);

            return values[i][j];

        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * modifie le label de l'arc (i, j)
     * @param i le sommet de départ
     * @param j le sommet d'arrivée
     * @param v le nouveau label de l'arc (i, j)
     */
    public void set(int i, int j, Type v)
    {

        try
        {
            checkIndex(i);
            checkIndex(j);

            values[i][j] = v;

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * le nombre de sommets
     * @return le nombre de sommets
     */
    public int size()
    {
        return getValues().length;
    }

    /**
     * @return un entier compris entre min et max
     */
    protected int generateInteger(int min, int max)
    {
        return (int) Math.floor(Math.random() * (max - min + 1)) + min;
    }

    /**
     * checkIndex
     * vérifie la validité d'un index
     * @param i index à tester
     * @throws ArrayIndexOutOfBoundsException
     */
    public void checkIndex(int i) throws ArrayIndexOutOfBoundsException
    {
        if ((i < 0) || (i >= getValues().length))
        {
            throw new ArrayIndexOutOfBoundsException("sommet non valide : " + i);
        }
    }

    /**
     * @return the values
     */
    public IValues[][] getValues()
    {
        return values;
    }

    /**
     * @param values the values to set
     */
    protected void setValues(Type[][] values)
    {
        this.values = values;
    }

    /**
     * teste l'existence d'un arc
     * @param i point d'entree de l'arc
     * @param j point de sortie de l'arc
     * @return vrai si l'arc (i, j) existe, faux sinon
     */
    public boolean exists(int i, int j)
    {
        checkIndex(i);
        checkIndex(j);
        return !((AbstractValues) noValue()).equals(get(i, j));
    }

    public boolean isSink(int i)
    {
        checkIndex(i);
        int j = 0;
        while ((j < values.length) && (!exists(i, j)) || (i == j))
        {
            j++;
        }
        return (j == values.length);
    }

    public boolean isSource(int i)
    {
        checkIndex(i);
        int j = 0;
        while ((j < values.length) && (!exists(j, i)) || (i == j))
        {
            j++;
        }
        return (j == values.length);
    }

    /**
     * ensemble Gamma+ de i
     * @param i sommet de départ
     * @return la liste des sommets étant extrémité d'un arc partant de i
     */
    List<Integer> gammaPlus(int i)
    {
        List<Integer> list = new ArrayList<Integer>();
        for (int k = 0; k < size(); k++)
        {
            if (exists(i, k))
            {
                list.add(k);
            }
        }
        return list;
    }

    /**
     * ensemble Gamma- de i
     * @param i sommet de départ
     * @return la liste des sommets étant extrémité d'un arc allant vers i
     */
    List<Integer> gammaMinus(int i)
    {
        List<Integer> list = new ArrayList<Integer>();
        for (int k = 0; k < size(); k++)
        {
            if (exists(k, i))
            {
                list.add(k);
            }
        }
        return list;
    }

    /**
     * affiche le graphe
     */
    public void show()
    {
        try
        {
            if (getValues() == null)
            {
                throw new NullPointerException("AbstractGraph::show : values==null");
            } else
            {
                String str = "";

                int T = size();

                for (int i = 0; i < T; i++)
                {
                    for (int j = 0; j < T; j++)
                    {

                        if (!exists(i, j))
                        {
                            str += noValue().toString();
                        } else
                        {
                            str += get(i, j).toString();
                        }
                        str += "\t";
                    }
                    str += "\n";
                }
                System.out.println(str);


            }


        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void serialize(String fileName) throws IOException
    {
        OutputStream os = new FileOutputStream(fileName);
        ObjectOutput oo = new ObjectOutputStream(os);
        try
        {
            oo.writeInt(values.length);
            oo.writeObject(values);
        } catch (IOException e)
        {
            System.out.println("Probleme a la serialization pour le fichier \"" + fileName + "\": " + e.getMessage());
            e.printStackTrace();
        } finally
        {
            oo.flush();
            oo.close();
            os.close();
        }
    }

    public void writeExternal(ObjectOutput out) throws IOException
    {
        out.writeInt(values.length);
        out.writeObject(values);
    }

    public void deserialize(String fileName) throws IOException, ClassNotFoundException
    {
        InputStream is = null;
        ObjectInput oi = null;
        try
        {
            is = new FileInputStream(fileName);
            oi = new ObjectInputStream(is);
            ;
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            System.out.println("Fichier serialise introuvable!");
        }
        try
        {

            int size = oi.readInt();
            values = new IValues[size][size];
            values = (IValues[][]) oi.readObject();
        } catch (IOException e)
        {
            System.out.println("Probleme a la deserialization du fichier \"" + fileName + "\": " + e.getMessage());
            e.printStackTrace();
        } finally
        {
            oi.close();
            is.close();
        }
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
    {
        int size = in.readInt();
        values = new IValues[size][size];
        values = (IValues[][]) in.readObject();
    }
}
