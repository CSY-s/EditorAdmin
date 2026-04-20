<template>
  <div id="app">
    <header-nav v-if="!isLoginPage" />
    <router-view />
    <footer-component v-if="!isLoginPage" />
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import HeaderNav from './components/HeaderNav.vue'
import FooterComponent from './components/FooterComponent.vue'

const route = useRoute()
const isLoginPage = computed(() => route.path === '/login')
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  line-height: 1.6;
  color: #333;
}

#app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

a {
  text-decoration: none;
  color: inherit;
}

.btn {
  display: inline-block;
  padding: 10px 20px;
  border-radius: 4px;
  border: none;
  cursor: pointer;
  font-size: 16px;
  transition: all 0.3s ease;
}

.btn-primary {
  background-color: #2c3e50;
  color: white;
}

.btn-primary:hover {
  background-color: #1a252f;
}

.btn-outline {
  background-color: transparent;
  border: 1px solid #2c3e50;
  color: #2c3e50;
}

.btn-outline:hover {
  background-color: #2c3e50;
  color: white;
}
</style>