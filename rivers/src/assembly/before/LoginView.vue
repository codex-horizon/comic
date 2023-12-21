<template>
  <div class="container">
    <el-form class="login" status-icon :model="form" ref="formRef">
      <el-form-item>
        <div class="form-item-avatar">
          <el-avatar :size="68" :src="avatarURL" fit="cover"/>
        </div>
      </el-form-item>
      <el-form-item prop="username" :rules="[{required: true, message: '用户名 空', trigger: 'blur'}]">
        <el-input v-model="form.username" placeholder="用户名" prefix-icon="User" clearable/>
      </el-form-item>
      <el-form-item prop="password" :rules="[{required: true, message: '密码 空', trigger: 'blur'}]">
        <el-input v-model="form.password" placeholder="密码" prefix-icon="Key" show-password clearable/>
      </el-form-item>
      <el-form-item>
        <div class="form-item-authentication">
          <el-button icon="Right" circle @click="handlerAuthentication('formRef')"/>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'LoginView',
  data() {
    return {
      avatarURL: require('@/assets/avatar.jpeg'),
      form: {
        username: '',
        password: ''
      }
    }
  },
  props: {},
  components: {},
  watch: {},
  methods: {
    handlerAuthentication(formName) {
      this.$refs[formName].validate(async (valid, object) => {
        console.log(valid, object);
        if (valid) {
          this.$message({
            message: '登录成功',
            type: 'success',
            showClose: true,
            grouping: true,
            center: true,
            duration: 1000,
            onClose: () => this.$router.push('/index')
          });
          return true;
        } else {
          return false;
        }
      });
    }
  }
};
</script>

<style scoped lang="scss">
.container {
  background: url(@/assets/sika-deer_1920.jpg) no-repeat center;
  background-size: cover;

  display: flex;
  align-items: center;
  justify-content: center;

  .login {
    width: 360px;
    padding: 36px 36px 18px;
    border-radius: 12px;
    box-shadow: 0 25px 50px rgba(0, 0, 0, 0.2);
    background: transparent;
    backdrop-filter: blur(10px);
    transition: 0.2s;

    .avatar, .captcha {
      width: 100px;
      background-color: transparent;
      cursor: pointer;
    }

    .form-item-avatar, .form-item-authentication {
      width: 100%;
      height: 100%;

      display: flex;
      align-items: center;
      justify-content: center;

      :deep(.el-avatar--circle) {
        background-color: transparent;
      }
    }

  }
}

</style>