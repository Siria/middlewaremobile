/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocco.filtro;

/**
 *
 * @author alessandra
 */

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;

/*
 * 
 */

public class VectorClock extends HashMap<String, Integer> implements Serializable
{

	private static final long serialVersionUID = 6668164199894268488L;

 void incrementClock(String pUnit)
	{

		if (this.containsKey(pUnit))
		{
			this.put(pUnit, this.get(pUnit).intValue() + 1);
		}

		else
		{
			this.put(pUnit, 1);
		}
	}

	public String[] getOrderedIDs()
	{
		String[] lResult = new String[this.size()];

		lResult = this.keySet().toArray(lResult);

		Arrays.sort(lResult);

		return lResult;
	}

	public Integer[] getOrderedValues()
	{
		Integer[] lResult = new Integer[this.size()];
		String[] lKeySet  = this.getOrderedIDs();

		int i = 0;
		for (String lKey : lKeySet)
		{
			lResult[i] = this.get(lKey);
			i++;
		}

		return lResult;
	}

	@Override
	public Integer get(Object key)
	{
		Integer lResult = super.get(key);

		if (lResult == null)
			lResult = 0;

		return lResult;
	}

	@Override
	public VectorClock clone()
	{
		return (VectorClock) super.clone();
	}

	@Override
	public String toString()
	{
		String[] lIDs		= this.getOrderedIDs();
		Integer[] lRequests = this.getOrderedValues();

		String lText = "(";

		for (int i = 0; i < lRequests.length; i++)
		{
			lText += lIDs[i];
			lText += " = ";
			lText += lRequests[i].toString();

			if (i + 1 < lRequests.length)
			{
				lText += ", ";
			}
		}

		lText += ")";

		return lText;
	}

	public static VectorClock max(VectorClock pOne, VectorClock pTwo)
	{

		VectorClock lResult = new VectorClock();


		for (String lEntry : pOne.keySet())
		{
			lResult.put(lEntry, pOne.get(lEntry));
		}

		for (String lEntry : pTwo.keySet())
		{
			if (!lResult.containsKey(lEntry) || lResult.get(lEntry) < pTwo.get(lEntry))
			{
				lResult.put(lEntry, pTwo.get(lEntry));
			}
		}

		return lResult;
	}


	public static VectorComparator compare(VectorClock pOne, VectorClock pTwo)
	{

		boolean lEqual	 = true;
		boolean lGreater = true;
		boolean lSmaller = true;

	
		for (String lEntry : pOne.keySet())
		{
		
			if (pTwo.containsKey(lEntry))
			{

				if (pOne.get(lEntry) < pTwo.get(lEntry))
				{
					lEqual	 = false;
					lGreater = false;
				}
				if (pOne.get(lEntry) > pTwo.get(lEntry))
				{
					lEqual	 = false;
					lSmaller = false;
				}
			}

			else if (pOne.get(lEntry) != 0)
			{
				lEqual	 = false;
				lSmaller = false;
			}
		}


		for (String lEntry : pTwo.keySet())
		{

			if (!pOne.containsKey(lEntry) && (pTwo.get(lEntry) != 0))
			{
				lEqual	 = false;
				lGreater = false;
			}
		}


		if (lEqual)
		{
			return VectorComparator.EQUAL;
		}
		else if (lGreater && !lSmaller)
		{
			return VectorComparator.GREATER;
		}
		else if (lSmaller && !lGreater)
		{
			return VectorComparator.SMALLER;
		}
		else
		{
			return VectorComparator.SIMULTANEOUS;
		}
	}
}