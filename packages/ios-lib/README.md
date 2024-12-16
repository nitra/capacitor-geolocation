# OSGeolocationLib-iOS

The `OSGeolocationLib` is a library built using `Swift` that is a simple template of a library that has a echo function.

## Usage

The library is available on CocoaPods as `OSGeolocationLib`. The following is an example of how to insert it into a Cordova plugin (through the `plugin.xml` file).

```xml
<podspec>
    <config>
        <source url="https://cdn.cocoapods.org/"/>
    </config>
    <pods use-frameworks="true">
        ...
        <pod name="OSGeolocationLib" spec="${version to use}" />
        ...
    </pods>
</podspec>
```

It can also be included as a dependency on other podspecs.

```ruby
Pod::Spec.new do |s|
  ...
  s.dependency 'OSGeolocationLib', '${version to use}'
  ...
end
```
