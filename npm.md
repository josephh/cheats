# deploy release to NPM private repo
(you should be logged in to NPM)
git checkout -b release/190305-1 // branch from develop
npm version patch
npm run deliver-release
npm deprecate, e.g.
npm deprecate @simplyhealth/component-library@">=5.2.0-rc13 <5.2.0-rc19" "deprecate release candidates"
