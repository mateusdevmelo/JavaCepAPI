package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import services.CEPRepository;

@Service
public class CEPService {
    private static final String API_URL = "https://brasilapi.com.br/api/cep/v1/";

    private final RestTemplate restTemplate;
    private final CEPRepository cepRepository;

    public void save(CEP cepEntity) {
    }

    public static class CEP {
        private String cep;
        private String state;
        private String city;
        private String neighborhood;
        private String street;
        private String service;

        public CEP() {
            // Construtor padrão vazio
        }

        public String getCep() {
            return cep;
        }

        public void setCep(String cep) {
            this.cep = cep;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getNeighborhood() {
            return neighborhood;
        }

        public void setNeighborhood(String neighborhood) {
            this.neighborhood = neighborhood;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }
    }

    @Autowired
    public void saveCEPInformation(CEP cep) {
        cepRepository.save(cep);
    }
    public CEPService(RestTemplate restTemplate, CEPRepository cepRepository) {
        this.restTemplate = restTemplate;
        this.cepRepository = cepRepository;
    }

    public void requestAndSaveCEPInformation(String cep) {
        String url = API_URL + cep;
        CEP response = restTemplate.getForObject(url, CEP.class);
        if (response != null) {
            // Mapeia a resposta da API para a entidade CEP
            CEP cepEntity = new CEP();
            cepEntity.setCep(response.getCep());
            cepEntity.setState(response.getState());
            cepEntity.setCity(response.getCity());
            cepEntity.setNeighborhood(response.getNeighborhood());
            cepEntity.setStreet(response.getStreet());
            cepEntity.setService(response.getService());

            // Salva os dados no banco de dados usando o repositório
            cepRepository.save(cepEntity);

            System.out.println("Dados do CEP salvos no banco de dados com sucesso:");
            System.out.println(cepEntity);
        } else {
            System.out.println("Erro ao fazer a solicitação à API.");
        }
    }
}