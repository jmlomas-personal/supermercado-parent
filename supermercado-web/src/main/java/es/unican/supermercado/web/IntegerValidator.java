package es.unican.supermercado.web;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "integerValidator")
public class IntegerValidator implements Validator {
	
	// Atributos de JSF
	private FacesContext context;
	private ResourceBundle bundle;
	private FacesMessage msg;
	
	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object value) throws ValidatorException {
		
		context = FacesContext.getCurrentInstance();
		bundle = context.getApplication().getResourceBundle(context, "msg");
		
		Integer componentValue = new Integer(value.toString());
		
		if (componentValue <= 0) {
			msg = new FacesMessage(bundle.getString("add_article_input_unidades_request"));		
	        msg.setSeverity(FacesMessage.SEVERITY_WARN);
			throw new ValidatorException(msg);
		}
		
	}



}
