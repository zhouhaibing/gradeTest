package com.zhb.test.classAndClass;

public class RoleInfo {
	private String roleId;
	private String roleName;
	private String roleLevel;
	private String roleVipLevel;
	private String serverId;
	private boolean growthplan;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(String roleLevel) {
		this.roleLevel = roleLevel;
	}

	public String getRoleVipLevel() {
		return roleVipLevel;
	}

	public void setRoleVipLevel(String roleVipLevel) {
		this.roleVipLevel = roleVipLevel;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	@Override
	public String toString() {
		return "RoleInfo [roleId=" + roleId + ", roleName=" + roleName + ", roleLevel=" + roleLevel + ", roleVipLevel=" + roleVipLevel + ", serverId=" + serverId + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o == null) {
			return false;
		}
		if (getClass() != o.getClass()) {
			return false;
		}
		RoleInfo other = (RoleInfo) o;
		return (roleId == other.roleId || (roleId != null && roleId.equals(other.roleId))) &&
			(roleName == other.roleName || (roleName != null && roleName.equals(other.roleName))) &&
			(roleLevel == other.roleLevel || (roleLevel != null && roleLevel.equals(other.roleLevel))) &&
			(roleVipLevel == other.roleVipLevel || (roleVipLevel != null && roleVipLevel.equals(other.roleVipLevel))) &&
			(serverId == other.serverId || (serverId != null && serverId.equals(other.serverId)));
	}

	@Override
	public int hashCode() {
		int result = 21;
		result = 31 * result + roleId == null ? 0 : roleId.hashCode();
		result = 31 * result + roleName == null ? 0 : roleName.hashCode();
		result = 31 * result + roleLevel == null ? 0 : roleLevel.hashCode();
		result = 31 * result + roleVipLevel == null ? 0 : roleVipLevel.hashCode();
		result = 31 * result + serverId == null ? 0 : serverId.hashCode();
		return result;
	}

	public boolean isGrowthplan() {
		return growthplan;
	}

	public void setGrowthplan(boolean growthplan) {
		this.growthplan = growthplan;
	}

}
