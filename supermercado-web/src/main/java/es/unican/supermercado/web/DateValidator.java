package es.unican.supermercado.web;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "dateValidator")
public class DateValidator implements Validator {
	
	// Atributos de JSF
	private FacesContext context;
	private ResourceBundle bundle;
	private FacesMessage msg;
	
	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object value) throws ValidatorException {
		
		context = FacesContext.getCurrentInstance();
		bundle = context.getApplication().getResourceBundle(context, "msg");
				 
		Calendar calRequest = GregorianCalendar.getInstance();
		calRequest.setTime( (Date) value );
		
		Calendar calToday = GregorianCalendar.getInstance();
		calToday.set(Calendar.HOUR_OF_DAY, 0);
		calToday.set(Calendar.MINUTE, 0);
		calToday.set(Calendar.SECOND, 0);
		calToday.set(Calendar.MILLISECOND, 0);
		
		if (calRequest.getTimeInMillis() < calToday.getTimeInMillis()) {
			
			msg = new FacesMessage(bundle.getString("cart_input_fecha_recogida_error"));		
	        msg.setSeverity(FacesMessage.SEVERITY_WARN);
	        
			throw new ValidatorException(msg);			
		}
		
	}

}
