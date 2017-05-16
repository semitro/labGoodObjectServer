package vt.smt.Commands;

import com.sun.istack.internal.NotNull;

import java.util.Locale;

/**
 * Просьба клиента сменить его локаль
 */
public class ChangeLocale implements ServerCommand {
    private Locale locale;

    public ChangeLocale(@NotNull Locale locale){
        this.locale = (Locale)locale.clone();
    }

    public Locale getLocale(){
        return locale;
    }
}
