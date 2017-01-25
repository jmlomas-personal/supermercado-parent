package es.unican.supermercado.web;

import java.text.DecimalFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "priceConverter")
public class PriceConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent comp, String value) {
		return value;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent comp, Object value) {
		double amountInEuros =  Double.parseDouble(value.toString());
		DecimalFormat df = new DecimalFormat("###,##0.##");
		return df.format(amountInEuros);
	}
}
