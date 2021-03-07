package com.zak.pro.dto;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
public class CloudinaryResponseDTO {

	private String public_id;
	private Long version;
	private String signature;
	private String created_at;
	private int bytes;
	private String url;
	private String secure_url;
	private String format;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("{ public_id : ");
		builder.append(this.public_id).append(", version : ").append(this.version).append(", signature : ")
				.append(this.signature).append(", created_at : ").append(this.created_at).append(", bytes : ")
				.append(this.bytes).append(", url : ").append(this.url).append(", secure_url : ")
				.append(this.secure_url).append(" }");
		return builder.toString();
	}

	public CloudinaryResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPublic_id() {
		return this.public_id;
	}

	public void setPublic_id(String public_id) {
		this.public_id = public_id;
	}

	public Long getVersion() {
		return this.version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getSignature() {
		return this.signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getCreated_at() {
		return this.created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public int getBytes() {
		return this.bytes;
	}

	public void setBytes(int bytes) {
		this.bytes = bytes;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSecure_url() {
		return this.secure_url;
	}

	public void setSecure_url(String secure_url) {
		this.secure_url = secure_url;
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
