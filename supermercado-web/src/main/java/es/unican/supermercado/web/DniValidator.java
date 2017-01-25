package es.unican.supermercado.web;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "dniValidator")
public class DniValidator implements Validator {

	private Pattern pattern;
	private Matcher matcher;
	
	// Atributos de JSF
	private FacesContext context;
	private ResourceBundle bundle;
	private FacesMessage msg;
	
	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object value) throws ValidatorException {
		
		context = FacesContext.getCurrentInstance();
		bundle = context.getApplication().getResourceBundle(context, "msg");
		
		String componentValue = value.toString();
		pattern = Pattern.compile("(([X-Z]{1})([-]?)(\\d{7})([-]?)([A-Z]{1}))|((\\d{8})([-]?)([A-Z]{1}))");
		matcher = pattern.matcher(componentValue);
		
		if (!matcher.find()) {						
			msg = new FacesMessage(bundle.getString("register_input_dni_validator"));		
	        msg.setSeverity(FacesMessage.SEVERITY_WARN);
			throw new ValidatorException(msg);
		}
		
	}



}
