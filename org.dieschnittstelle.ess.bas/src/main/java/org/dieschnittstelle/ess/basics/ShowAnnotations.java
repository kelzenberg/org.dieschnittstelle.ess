package org.dieschnittstelle.ess.basics;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.basics.annotations.AnnotatedStockItemBuilder;
import org.dieschnittstelle.ess.basics.annotations.StockItemProxyImpl;
import org.dieschnittstelle.ess.basics.reflection.ReflectedStockItemBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.dieschnittstelle.ess.utils.Utils.*;

public class ShowAnnotations {

    protected static Logger logger = LogManager
            .getLogger(ReflectedStockItemBuilder.class);

    public static void main(String[] args) {
        // we initialise the collection
        StockItemCollection collection = new StockItemCollection(
                "stockitems_annotations.xml", new AnnotatedStockItemBuilder());
        // we load the contents into the collection
        collection.load();

        for (IStockItem consumable : collection.getStockItems()) {
            ;
            showAttributes(((StockItemProxyImpl) consumable).getProxiedObject());
        }

        // we initialise a consumer
        Consumer consumer = new Consumer();
        // ... and let them consume
        consumer.doShopping(collection.getStockItems());
    }

    /*
     * TODO BAS2
     *
     *  {<einfacher Klassenname> <attr1>:<Wert von attr1>, ...}
     *  {Milch menge:20, markenname:Mark Brandenburg}
     */
    private static void showAttributes(Object consumable) {
        Class<?> consumableClass = consumable.getClass();

        StringBuilder output = new StringBuilder();
        output.append(consumableClass.getSimpleName());

        for (Field field : consumableClass.getDeclaredFields()) {
            String fieldName = field.getName();
            Method getter = null;

            for (Method method : consumableClass.getDeclaredMethods()) {
                String methodName = method.getName().toLowerCase();
                String getFieldName = "get" + fieldName.toLowerCase();

                if (methodName.equals(getFieldName)) {
                    getter = method;
                    break;
                }
            }

            if (getter == null) {
                break;
            }

            Object fieldValue = null;

            try {
                fieldValue = getter.invoke(consumable).toString();
            } catch (Exception e) {
                logger.error("cannot invoke getter: " + e);
                e.printStackTrace();
            }

            output.append(String.format(" %s:%s", fieldName, fieldValue));
        }

        show("[class] " + consumableClass
                + "\n------------ [output] " + output.toString());
    }
}
