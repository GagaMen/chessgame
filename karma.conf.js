module.exports = function (config) {
    config.set({
        basePath: '',
        frameworks: ['mocha', 'requirejs', 'chai'],
        plugins: [
            "karma-chai",
            //"karma-chrome-launcher",
            //"karma-coverage",
            "karma-mocha",
            "karma-mocha-reporter",
            "karma-phantomjs-launcher",
            "karma-requirejs"
        ],
        files: [
            {pattern: './client/dist/js/lib/*.js', included: false},
            {pattern: './client/dist/js/*.js', included: false},
            {pattern: './client/dist/js/test/*_test.js', included: false},
            {pattern: './client/dist/js/test/start-tests.js', included: true}
        ],
        excludes: [
            'require*.js'
        ],
        // preprocessors: {
        //     './client/dist/js/client.js': 'coverage'
        // },
        // coverageReporter: {
        //     type : 'text-summary',
        //     dir : 'coverage/',
        //     includeAllSources : true
        // },
        reporters: ['mocha'],// 'coverage'],
        browsers: ['PhantomJS'],// 'Chrome'],
        port: 9876,
        colors: true,
        logLevel: config.LOG_INFO,
        autoWatch: true,
        singleRun: false,
        concurrency: Infinity
    })
};