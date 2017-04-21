package fr.kiabi.poc.jcache.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * A DeliveryPoint.
 */
public class DeliveryPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(max = 20)
    private String code;

    @NotNull
    @Size(max = 2)
    private String countryCode;

    @NotNull
    @Size(max = 3)
    private String carrierCode;

    @Size(max = 128)
    private String name;

    @Size(max = 3)
    private String type;

    private Boolean displayable;

    private Boolean disabled;

    private Date creationDate;

    private Date lastModificationDate;

    private String zoneCode;

    private String url;

    private Date openingDate;

    private Date closingDate;

    private Double latitude;

    private Double longitude;

    private Set<OpeningHours> openingHours;

    private Map<String, String> properties;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isDisplayable() {
        return displayable;
    }

    public void setDisplayable(Boolean displayable) {
        this.displayable = displayable;
    }

    public Boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Set<OpeningHours> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(Set<OpeningHours> openingHours) {
        this.openingHours = openingHours;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeliveryPoint deliveryPoint = (DeliveryPoint) o;
        if (deliveryPoint.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, deliveryPoint.id) &&
                Objects.equals(code, deliveryPoint.code) &&
                Objects.equals(countryCode, deliveryPoint.countryCode) &&
                Objects.equals(carrierCode, deliveryPoint.carrierCode) &&
                Objects.equals(name, deliveryPoint.name) &&
                Objects.equals(type, deliveryPoint.type) &&
                Objects.equals(displayable, deliveryPoint.displayable) &&
                Objects.equals(disabled, deliveryPoint.disabled) &&
                Objects.equals(creationDate, deliveryPoint.creationDate) &&
                Objects.equals(lastModificationDate, deliveryPoint.lastModificationDate) &&
                Objects.equals(zoneCode, deliveryPoint.zoneCode) &&
                Objects.equals(url, deliveryPoint.url) &&
                Objects.equals(openingDate, deliveryPoint.openingDate) &&
                Objects.equals(closingDate, deliveryPoint.closingDate) &&
                Objects.equals(latitude, deliveryPoint.latitude) &&
                Objects.equals(longitude, deliveryPoint.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DeliveryPoint{" +
                "id=" + id +
                ", code='" + code + "'" +
                ", countryCode='" + countryCode + "'" +
                ", carrierCode='" + carrierCode + "'" +
                ", name='" + name + "'" +
                ", type='" + type + "'" +
                ", displayable='" + displayable + "'" +
                ", disabled='" + disabled + "'" +
                ", creationDate='" + creationDate + "'" +
                ", lastModificationDate='" + lastModificationDate + "'" +
                ", zoneCode='" + zoneCode + "'" +
                ", url='" + url + "'" +
                ", openingDate='" + openingDate + "'" +
                ", closingDate='" + closingDate + "'" +
                ", latitude='" + latitude + "'" +
                ", longitude='" + longitude + "'" +
                '}';
    }
}
