package blocco.proxy;


import java.lang.reflect.*;
public class ProxyTarget implements InvocationHandler{
    public static Object createProxy(Object obj){
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                                                  obj.getClass().getInterfaces(),
                                                  new ProxyTarget(obj));
     }

      private Object target;
      private ProxyTarget(Object target)
      {
           this.target = target;
      }
 
    @Override
      public Object invoke(Object proxy, Method method, Object[] args)
      {
            Object result = null;
            try{
                result = method.invoke(target,args);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            return result;
       }
}
