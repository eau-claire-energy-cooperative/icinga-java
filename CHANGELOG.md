# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/)

## 0.2.6

### Added

- added the CONTAINS_VALUE filter operator, this uses the "in" keyword to (search for values within an array)[https://icinga.com/docs/icinga2/latest/doc/12-icinga2-api/#filter-variables]. 
- added GNU GPL v3 license

### Removed

- removed Ant ```build.xml``` file. Not needed anymore

## 0.2.5

### Changed

- changed min java version to 1.8

### Removed

- removed persistent as an Acknowledge Action, this appears to have been removed from the API

## 0.2.4

### Added

- added persistent flag for Acknowledgements

## 0.2.3

### Added

- added ```hasResult()``` method to ```IcingaApi``` class. This tests if any result is returned from a query object

## 0.2.2

### Added

- made ```ResponseRow``` class implement ```JSONAware```. This allows it to be exported as a JSON object

## 0.2.1

### Fixed

- removed some println debug statements

## 0.2.0

### Changed

- merged icinga2 branch into master. This updates the API to work with Icinga2

### Deprecated

- all Icinga1 functions. These can be found in the tagged icinga_1 branch