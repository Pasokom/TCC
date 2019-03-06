package db.pojo;

public class HolidayDB extends AppointmentDB {

    private int cod_feriado;
    private String nome;
    private int tipo_data;
    private int dia;
    private int dia_semana;
    private int semana;
    private int mes;
    private long data;

    private int dia_mes;

    public HolidayDB() {
        super("holiday");
    }

    /**
     * @return the cod_feriado
     */
    public int getCod_feriado() {
        return cod_feriado;
    }

    /**
     * @param cod_feriado the cod_feriado to set
     */
    public void setCod_feriado(int cod_feriado) {
        this.cod_feriado = cod_feriado;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the tipo_data
     */
    public int getTipo_data() {
        return tipo_data;
    }

    /**
     * @param tipo_data the tipo_data to set
     */
    public void setTipo_data(int tipo_data) {
        this.tipo_data = tipo_data;
    }

    /**
     * @return the dia
     */
    public int getDia() {
        return dia;
    }

    /**
     * @param dia the dia to set
     */
    public void setDia(int dia) {
        this.dia = dia;
    }

    /**
     * @return the dia_semana
     */
    public int getDia_semana() {
        return dia_semana;
    }

    /**
     * @param dia_semana the dia_semana to set
     */
    public void setDia_semana(int dia_semana) {
        this.dia_semana = dia_semana;
    }

    /**
     * @return the semana
     */
    public int getSemana() {
        return semana;
    }

    /**
     * @param semana the semana to set
     */
    public void setSemana(int semana) {
        this.semana = semana;
    }

    /**
     * @return the mes
     */
    public int getMes() {
        return mes;
    }

    /**
     * @param mes the mes to set
     */
    public void setMes(int mes) {
        this.mes = mes;
    }

    /**
     * @return the data
     */
    public long getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(long data) {
        this.data = data;
    }

    /**
     * @return the dia_mes
     */
    public int getDia_mes() {
        return dia_mes;
    }

    /**
     * @param dia_mes the dia_mes to set
     */
    public void setDia_mes(int dia_mes) {
        this.dia_mes = dia_mes;
    }
}