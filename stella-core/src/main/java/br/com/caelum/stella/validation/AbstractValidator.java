package br.com.caelum.stella.validation;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.stella.MessageProducer;
import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.Validator;

public abstract class AbstractValidator<T> implements Validator<T>{
	private final MessageProducer messageProducer;
	public AbstractValidator(MessageProducer messageProducer) {
		super();
		this.messageProducer = messageProducer;
	}

	protected List<ValidationMessage> getValidationMessages(List<InvalidValue> invalidValues) {
		List<ValidationMessage> messages = new ArrayList<ValidationMessage>();
		for (InvalidValue invalidValue : invalidValues) {
			ValidationMessage message = messageProducer.getMessage(invalidValue);
			messages.add(message);
		}
		return messages;
	}

	public List<ValidationMessage> getValidationMessages(T value) {
		List<InvalidValue> invalidValues = getInvalidValues(value);
		List<ValidationMessage> messages = getValidationMessages(invalidValues);
		return messages;
	}

	public void assertValid(T value) {
		List<InvalidValue> errors = getInvalidValues(value);
		if (!errors.isEmpty()) {
			throw new InvalidStateException(getValidationMessages(errors));
		}
	}

	protected abstract List<InvalidValue> getInvalidValues(T value);

}