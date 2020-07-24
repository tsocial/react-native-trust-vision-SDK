
Pod::Spec.new do |s|
  s.name         = "RNTrustVisionRnsdkFramework"
  s.version      = "1.0.11"
  s.summary      = "RNTrustVisionRnsdkFramework"
  s.description  = <<-DESC
                  RNTrustVisionRnsdkFramework
                   DESC
  s.homepage     = "https://github.com/tsocial/TrustVisionRNSDKFramework"
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "author" => "author@domain.cn" }
  s.platform     = :ios, "9.0"
  s.source       = { :git => "https://github.com/author/RNTrustVisionRnsdkFramework.git", :tag => "release" }
#  s.source_files  = "RNTrustVisionRnsdkFramework/**/*.{h,m}"
  s.source_files  = "ios/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  s.dependency 'TrustVisionSDKFramework', '~> 1.0.11'

end
