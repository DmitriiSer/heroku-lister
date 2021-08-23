package serikov.dmitrii.lister.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author Dmitrii Serikov
 */
@Document
@ApiModel(value = "UserProfile", description = "User profile")
public class UserProfile {

	@ApiModelProperty(required = true, example = "John Doe")
	private String username;

	@ApiModelProperty(example = "SECURE_PASSWORD")
	private String password;

	@ApiModelProperty(example = "Avatar #1")
	private String avatar;

	@ApiModelProperty(example = "true")
	private boolean loggedIn;

	@ApiModelProperty(example = "5000")
	private int timeout;

	private List<String> lists;

	public static boolean isValid(final UserProfile userProfile) {
		final boolean res = userProfile != null && userProfile.getUsername() != null
				&& !userProfile.getUsername().isEmpty();
		return res;
	}

	public static UserProfile sanitize(final UserProfile userProfile) {
		userProfile.clearPassword();
		return userProfile;
	}

	//
	public UserProfile() {
	}

	public UserProfile(String username, String avatar) {
		this.username = username;
		this.avatar = avatar;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void clearPassword() {
		this.password = "";
	}

	public String getAvatar() {
		return avatar;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public int getListsSize() {
		if (lists != null) {
			return lists.size();
		}
		return 0;
	}

	public List<String> getLists() {
		if (lists == null) {
			lists = new ArrayList<>();
		}
		return lists;
	}

	public void setListTitles(List<String> lists) {
		this.lists = lists;
	}

	public boolean removeList(String listName) {
		try {
			this.lists.remove(listName);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(avatar, lists, loggedIn, password, timeout, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserProfile other = (UserProfile) obj;
		return Objects.equals(avatar, other.avatar) && Objects.equals(lists, other.lists) && loggedIn == other.loggedIn
				&& Objects.equals(password, other.password) && timeout == other.timeout
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserProfile [username=").append(username).append(", password=").append(password)
				.append(", avatar=").append(avatar).append(", loggedIn=").append(loggedIn).append(", timeout=")
				.append(timeout).append(", lists=").append(lists).append("]");
		return builder.toString();
	}

}