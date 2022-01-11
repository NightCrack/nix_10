package ua.com.alevel.config;

import java.net.URL;

public class GenerateImplementationFromInterfaceFactory {

//    private static final Reflections scan = new Reflections("ua.com.alevel");
//
//    public static <IFC> IFC generateImplementation(Class<IFC> ifc) {
//        if (ifc.isInterface()) {
//            Set<Class<? extends IFC>> implementations = scan.getSubTypesOf(ifc);
//            for (Class<? extends IFC> implementation : implementations) {
//                if (implementation.isAnnotationPresent(ActiveClass.class)) {
//                    try {
//                        return implementation.getDeclaredConstructor().newInstance();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//        return null;
//    }

    public <ENTITY> ENTITY generateImplementation(Class<ENTITY> ifc) {
        if (ifc.isInterface()) {
            URL packageLocation = ifc.getProtectionDomain().getCodeSource().getLocation();
        }
        return null;
    }
}
