package classes.util;


public final class ViewHelper {

    private ViewHelper() {
    }

    public static String getValuesAsString() {
        if (!InMemoryStorage.getValues().isEmpty())
            return InMemoryStorage.getValues().get(InMemoryStorage.getValues().size() - 1);
        else
            return null;
    }

}
