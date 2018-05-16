package console;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;


@Retention(RUNTIME)
public @interface AsCommand {
	String getName();
	String getHelpText();

}
