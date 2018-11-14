package ua.nure.kn.mironova.usermanagement;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -574566248786051001L;
	private String firstName;
	private String lastName;
	private Date dateofBirth;
	private Long id;
	
	public User() {
	}
	
	/**
	 * @param firstName
	 * @param lastName
	 * @param dateofBirth
	 * @param id
	 */
	public User(String firstName, String lastName, Date dateofBirth, Long id) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateofBirth = dateofBirth;
		this.id = id;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the dateofBirth
	 */
	public Date getDateofBirth() {
		return dateofBirth;
	}

	/**
	 * @param dateofBirth the dateofBirth to set
	 */
	public void setDateofBirth(Date dateofBirth) {
		this.dateofBirth = dateofBirth;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dateofBirth == null) ? 0 : dateofBirth.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (dateofBirth == null) {
			if (other.dateofBirth != null)
				return false;
		} else if (!dateofBirth.equals(other.dateofBirth))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}

	/**
	 * public String toString()
	 * @return a string with all user data
	 */
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName
				+ ", dateofBirth=" + dateofBirth + ", id=" + id + "]";
	}
	/**
	* public String getFullName()
	* requires: no restrictions
	* @return full username in the format lastName + "," + firstName
	*/
	public String getFullName() {
		return(new StringBuilder (new StringBuilder(getLastName()))
		.append(", ")
		.append(getFirstName())
		.toString());
		 
	}
	/**
	* public int getAge()
	* requires: no restrictions
	* @return age of user
	*/
	public int getAge() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		Calendar currentDate = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		calendar.setTime(getDateofBirth());
		int year = calendar.get(Calendar.YEAR);
		int age =  currentYear - year;
		if (calendar.get(Calendar.MONTH) > currentDate.get(Calendar.MONTH)) {
			age--;
		}
		if (calendar.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH) &&
			calendar.get(Calendar.DAY_OF_MONTH) > currentDate.get(Calendar.DAY_OF_MONTH)) { 
				age--;
		}
		return age;
	} 	
	
}
