module.exports = {
    "env": {
        "browser": true,
        "es6": true,
        "node": true
    },
    "extends": "eslint:recommended",
    "globals": {
        "Atomics": "readonly",
        "SharedArrayBuffer": "readonly"
    },
    "parserOptions": {
        "ecmaVersion": 2018,
        "sourceType": "module"
    },
    "rules": {
        // enable additional rules
        "indent": ["error", 2],
        "linebreak-style": ["error", "unix"],
        "quotes": ["error", "double"],
        "semi": ["error", "always"],
        "keyword-spacing":["error", { "before": true }],
        "object-curly-spacing": ["error","always"],
        "comma-spacing":["error",{"before":false,"after":true}],
        "no-console": ["error"],
        "no-unused-vars": ["warn"],
            "prefer-const": ["warn", {
            "destructuring": "any",
            "ignoreReadBeforeAssign": false
        }]
    }
};
