package ada.tech.agenda.util;

import ada.tech.agenda.model.Contato;
import ada.tech.agenda.model.Mensagem;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsTwilio {
    public void enviarSms(Mensagem mensagem) {
        Twilio.init(
                System.getenv("TWILIO_ACCOUNT_SID"),
                System.getenv("TWILIO_AUTH_TOKEN"));

        Message.creator(
                        new PhoneNumber("+55" + mensagem.getTelefone()),
                        new PhoneNumber(System.getenv("TWILIO_PHONE_NUMBER")),
                        mensagem.getTexto())
                .create();


    }


}
