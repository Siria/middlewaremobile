package blocco.proxy;


import configuratore.AlgoConfSoglie;
import java.util.HashMap;




public interface AlgoritmoProxy{
    AlgoConfSoglie v = new AlgoConfSoglie();
    HashMap hvalue = (HashMap) v.valuta(v);
    public Object valuta(Object messaggio);
}

