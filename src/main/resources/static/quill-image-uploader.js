// src/main/resources/static/js/quill-image-uploader.js
(function() {
    // 扩展Quill的图片上传模块
    const ImageUploader = function(quill, options) {
        this.quill = quill;
        this.options = options;
        this.range = null;
        
        if (typeof this.options.upload !== 'function') {
            console.warn('[Quill ImageUploader] upload function is not defined');
        }
        
        // 监听工具栏的图片按钮点击
        var toolbar = this.quill.getModule('toolbar');
        if (toolbar) {
            toolbar.addHandler('image', this.selectLocalImage.bind(this));
        }
    };
    
    ImageUploader.prototype.selectLocalImage = function() {
        this.range = this.quill.getSelection();
        this.fileHolder = document.createElement('input');
        this.fileHolder.setAttribute('type', 'file');
        this.fileHolder.setAttribute('accept', 'image/*');
        this.fileHolder.onchange = this.fileChanged.bind(this);
        this.fileHolder.click();
    };
    
    ImageUploader.prototype.fileChanged = function() {
        const file = this.fileHolder.files[0];
        
        // 文件验证
        if (!file) return;
        
        if (!file.type.match(/^image\//)) {
            alert('请选择图片文件');
            return;
        }
        
        if (file.size > 5 * 1024 * 1024) {
            alert('图片大小不能超过5MB');
            return;
        }
        
        // 插入占位图片
        const reader = new FileReader();
        reader.onload = (e) => {
            const placeholder = e.target.result;
            this.insertBase64Image(placeholder);
        };
        reader.readAsDataURL(file);
        
        // 上传图片
        this.uploadImage(file);
    };
    
    ImageUploader.prototype.insertBase64Image = function(url) {
        const range = this.range;
        this.quill.insertEmbed(range.index, 'image', url);
    };
    
    ImageUploader.prototype.uploadImage = function(file) {
        const upload = this.options.upload;
        
        if (typeof upload !== 'function') {
            console.warn('[Quill ImageUploader] upload function is not defined');
            return;
        }
        
        upload(file)
            .then((imageUrl) => {
                // 替换占位图片为服务器返回的URL
                this.insertToEditor(imageUrl);
            })
            .catch((error) => {
                console.error('Image upload failed:', error);
                alert('图片上传失败: ' + error.message);
                
                // 删除占位图片
                this.quill.deleteText(this.range.index, 1);
            });
    };
    
    ImageUploader.prototype.insertToEditor = function(url) {
        // 将占位图片替换为服务器返回的URL
        this.quill.deleteText(this.range.index, 1);
        this.quill.insertEmbed(this.range.index, 'image', url);
        
        // 移动光标到图片后面
        this.quill.setSelection(this.range.index + 1);
    };
    
    // 注册模块
    if (window.Quill) {
        window.Quill.register('modules/imageUploader', ImageUploader);
    }
})();