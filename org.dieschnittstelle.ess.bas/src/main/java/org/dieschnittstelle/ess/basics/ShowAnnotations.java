package org.dieschnittstelle.ess.basics;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.basics.annotations.AnnotatedStockItemBuilder;
import org.dieschnittstelle.ess.basics.annotations.DisplayAs;
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
            String fieldName = null;
            boolean hasAnnotation = field.isAnnotationPresent(DisplayAs.class);
            if (hasAnnotation) {
                DisplayAs currentDisplayAsAnnotation = field.getAnnotation(DisplayAs.class);
                fieldName = currentDisplayAsAnnotation.value();
            } else {
                fieldName = field.getName();
            }

            Method getter = null;

            for (Method method : consumableClass.getDeclaredMethods()) {
                String methodName = null;
                if (method.isAnnotationPresent(DisplayAs.class)) {
                    DisplayAs currentDisplayAsAnnotation = method.getAnnotation(DisplayAs.class);
                    methodName = currentDisplayAsAnnotation.value();
                } else {
                    methodName = method.getName();
                }

                methodName = methodName.toLowerCase();
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

            output.append(String.format(" %s:%s", hasAnnotation ? fieldName + "(annotated)" : fieldName, fieldValue));
        }

        show("[class] " + consumableClass
                + "\n------------ [output] " + output.toString());
    }
}
