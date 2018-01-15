var tests = [];
for (var file in window.__karma__.files) {
    if (/\_test\.js$/.test(file)) {
        tests.push(file);
    }
}
requirejs.config({
    baseUrl: '/base/client/dist/js',
    paths: {
        'kotlin': 'lib/kotlin',
        'kotlin-test': 'lib/kotlin-test'
    },
    deps: tests,
    callback: mocha.run
});