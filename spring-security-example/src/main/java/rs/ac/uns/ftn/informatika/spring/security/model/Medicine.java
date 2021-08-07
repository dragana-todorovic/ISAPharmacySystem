package rs.ac.uns.ftn.informatika.spring.security.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="MEDICINE")
public class Medicine {
	 @Id
	 @Column(name = "id")
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	 
    @Column(name = "code")
    private String code;

   @Column(name = "name")
   private String name;
   
   @Column(name = "shape")
   private MedicineShape shape=MedicineShape.CAPSULE;
   
   @Column(name = "content")
   private String content;
   
   @Column(name = "producer")
   private String producer;
   
   @Column(name = "withprescription")
   private boolean withprescription;
   
   @ElementCollection(fetch = FetchType.LAZY)
   private Set<String> substituteMedicineCodes = new HashSet<String>();
   
   @Column(name = "notes")
   private String notes;
   
   @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Rating> ratings = new HashSet<Rating>();

   
   
public Medicine() {
	super();
}

public Medicine(Long id, String code, String name, MedicineShape shape, String content, String producer,
		boolean withprescription, Set<String> substituteMedicineCodes, String notes) {
	super();
	this.id = id;
	this.code = code;
	this.name = name;
	this.shape = shape;
	this.content = content;
	this.producer = producer;
	this.withprescription = withprescription;
	this.substituteMedicineCodes = substituteMedicineCodes;
	this.notes = notes;
}

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

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public MedicineShape getShape() {
	return shape;
}

public void setShape(MedicineShape shape) {
	this.shape = shape;
}

public String getContent() {
	return content;
}

public void setContent(String content) {
	this.content = content;
}

public String getProducer() {
	return producer;
}

public void setProducer(String producer) {
	this.producer = producer;
}

public boolean isWithprescription() {
	return withprescription;
}

public void setWithprescription(boolean withprescription) {
	this.withprescription = withprescription;
}

public Set<String> getSubstituteMedicineCodes() {
	return substituteMedicineCodes;
}

public void setSubstituteMedicineCodes(Set<String> substituteMedicineCodes) {
	this.substituteMedicineCodes = substituteMedicineCodes;
}

public String getNotes() {
	return notes;
}

public void setNotes(String notes) {
	this.notes = notes;
}
   
   
}
