<template>
  <div class="container">
    <el-form class="login" status-icon :model="form" ref="formRef">
      <el-form-item>
        <div class="form-item-avatar">
          <el-avatar :size="68" :src="avatarURL" fit="cover"/>
        </div>
      </el-form-item>
      <el-form-item prop="username" :rules="[{required: true, message: '账号 空', trigger: 'blur'}]">
        <el-input v-model="form.username" placeholder="账号" prefix-icon="User" clearable/>
      </el-form-item>
      <el-form-item prop="password" :rules="[{required: true, message: '密码 空', trigger: 'blur'}]">
        <el-input v-model="form.password" placeholder="密码" prefix-icon="Key" show-password clearable/>
      </el-form-item>
      <el-form-item>
        <div class="form-item-authentication">
          <el-button icon="Right" circle @click="handlerAuthentication('formRef')" :disabled="disabled"/>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {userApi} from '@/api/index.js';
import {setSession} from "@/utils";

export default {
  name: 'LoginView',
  data() {
    return {
      avatarURL: require('@/assets/avatar.jpeg'),
      form: {
        username: '',
        password: ''
      },
      disabled: false
    }
  },
  props: {},
  components: {},
  watch: {},
  methods: {
    handlerAuthentication(formName) {
      this.$refs[formName].validate(async valid => {
        if (valid) {
          userApi.authentication(this.form).then(res => {
            this.disabled = true;
            if ('Biz_Ok_Response' === res.code) {
              const data = res.data;
              for (const key in data) {
                setSession(key, JSON.stringify(data[key]));
                document.cookie = `UUID=${key}`;
              }
              this.$message({
                message: '登录成功',
                type: 'success',
                grouping: true,
                center: true,
                duration: 1000,
                onClose: () => this.$router.push('/index')
              });
              return true;
            }
            if ('Biz_Failed_Response' === res.code) {
              this.$message.error(res.message);
            }
            return false;
          })
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
  background: url(@/assets/earth.jpg) no-repeat center;
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