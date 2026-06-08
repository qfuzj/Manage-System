import useUserStore from '@/store/modules/user'

function authPermission(permission) {
  // 超级管理员拥有所有权限
  const all_permission = "*:*:*";
  // 获取当前用户权限列表
  const permissions = useUserStore().permissions
  // 如果需要验证的权限存在，则进行验证，否则不验证
  if (permission && permission.length > 0) {
    return permissions.some(v => {
      return all_permission === v || v === permission
    })
  } else {
    return false
  }
}

function authRole(role) {
  const super_admin = "admin";
  const roles = useUserStore().roles
  if (role && role.length > 0) {
    return roles.some(v => {
      return super_admin === v || v === role
    })
  } else {
    return false
  }
}

export default {
  // 验证用户是否具备某权限
  hasPermi(permission) {
    return authPermission(permission);
  },
  // 验证用户是否含有指定权限，只需包含其中一个
  hasPermiOr(permissions) {
    return permissions.some(item => {
      return authPermission(item)
    })
  },
  // 验证用户是否含有指定权限，必须全部拥有
  hasPermiAnd(permissions) {
    return permissions.every(item => {
      return authPermission(item)
    })
  },
  // 验证用户是否具备某角色
  hasRole(role) {
    return authRole(role);
  },
  // 验证用户是否含有指定角色，只需包含其中一个
  hasRoleOr(roles) {
    return roles.some(item => {
      return authRole(item)
    })
  },
  // 验证用户是否含有指定角色，必须全部拥有
  hasRoleAnd(roles) {
    return roles.every(item => {
      return authRole(item)
    })
  }
}
