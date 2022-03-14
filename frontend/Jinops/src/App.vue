<template>
  <Hello></Hello>
</template>

<script>
import Hello from './components/Hello.vue'
import HelloWorld from './components/HelloWorld.vue'
import Example from './components/Example'

export default {
  name: 'App',
  components: {
    Example,
    HelloWorld,
    Hello
  },
  runtimeCompiler: true
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
