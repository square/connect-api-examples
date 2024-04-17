import globals from "globals";
import pluginJs from "@eslint/js";
import tseslint from "typescript-eslint";

export default [
  { languageOptions: { globals: globals.node } },
  pluginJs.configs.recommended,
  ...tseslint.configs.recommended,

  // Add TypeScript parser
  { parser: "@typescript-eslint/parser" },

  // Add TypeScript rules to allow non-strict null checks
  {
    files: ["*.ts", "*.tsx"],
    rules: {
      // Allow optional chaining
      "@typescript-eslint/prefer-optional-chain": "off",
      
      // Allow nullish coalescing
      "@typescript-eslint/prefer-nullish-coalescing": "off"
    }
  }
];
