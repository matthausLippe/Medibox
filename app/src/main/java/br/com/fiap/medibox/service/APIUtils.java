package br.com.fiap.medibox.service;


public class APIUtils {

    private APIUtils(){
    };

    public static CaixaService getCaixaService(){
        return RetrofitConfig
                .getRetrofitInstance().create(CaixaService.class);
    }

    public static ClienteService getClienteService(){
        return RetrofitConfig
                .getRetrofitInstance().create(ClienteService.class);
    }

    public static  EnderecoService getEnderecoService(){
        return RetrofitConfig.getRetrofitInstance().create(EnderecoService.class);
    }

    public static  GavetaService getGavetaService(){
        return RetrofitConfig.getRetrofitInstance().create(GavetaService.class);
    }

    public static  MedicamentoService getMedicamentoService(){
        return RetrofitConfig.getRetrofitInstance().create(MedicamentoService.class);
    }

    public static  ResidenteMedicamentoService getResidenteMedicamentoService(){
        return RetrofitConfig.getRetrofitInstance().create(ResidenteMedicamentoService.class);
    }

    public static  ResidenteService getResidenteService(){
        return RetrofitConfig.getRetrofitInstance().create(ResidenteService.class);
    }

    public static  TimeLineService getTimeLineService(){
        return RetrofitConfig.getRetrofitInstance().create(TimeLineService.class);
    }
}
