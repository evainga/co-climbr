import { shallowMount } from '@vue/test-utils'
import App from '@/App.vue'

// see https://vue-test-utils.vuejs.org/guides/using-with-vue-router.html#testing-components-that-use-router-link-or-router-view
describe('App component', () => {
  it('renders the correct markup without crashing', () => {
    const wrapper = shallowMount(App, {
      stubs: ['router-link', 'router-view']
    })
    expect(wrapper.html()).toContain('<div id="app">')
    expect(wrapper.html()).toContain('<div id="nav">')
    expect(wrapper.html()).not.toContain('<div id="nakjhkv">')
  })
})
