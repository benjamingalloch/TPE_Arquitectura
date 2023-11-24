package microservie_testJWT.Dto;

import java.time.LocalDate;



import lombok.Data;

@Data
public class AccountDTO {
	
	private Integer idCuenta;
	private double saldo;
	private LocalDate fechaAlta;

	public AccountDTO() {
	}

	public AccountDTO(Integer idCuenta, double saldo, LocalDate fechaAlta) {
		super();
		this.idCuenta = idCuenta;
		this.saldo = saldo;
		this.fechaAlta = fechaAlta;
	}

	public Integer getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(Integer idCuenta) {
		this.idCuenta = idCuenta;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	@Override
	public String toString() {
		return "DtoCuenta [idCuenta=" + idCuenta + ", saldo=" + saldo + ", fechaAlta=" + fechaAlta + "]";
	}


}
