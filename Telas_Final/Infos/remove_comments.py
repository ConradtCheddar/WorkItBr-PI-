import os
import re

def remove_comments_from_java(content):
    lines = content.split('\n')
    result = []
    in_multiline_comment = False
    in_javadoc = False
    
    for line in lines:
        stripped = line.strip()
        
        # Check if entering multiline comment
        if '/*' in line and not in_multiline_comment:
            in_multiline_comment = True
            if '/**' in line:
                in_javadoc = True
            # Handle single-line /* */ comments
            if '*/' in line:
                in_multiline_comment = False
                in_javadoc = False
                # Keep the line if there's code after */
                after_comment = line.split('*/', 1)[-1].strip()
                if after_comment:
                    result.append(after_comment)
                continue
            else:
                continue
        
        # Check if exiting multiline comment
        if in_multiline_comment or in_javadoc:
            if '*/' in line:
                in_multiline_comment = False
                in_javadoc = False
                # Keep the line if there's code after */
                after_comment = line.split('*/', 1)[-1].strip()
                if after_comment:
                    result.append(after_comment)
            continue
        
        # Skip single-line comments
        if stripped.startswith('//'):
            continue
        
        # Handle inline comments
        if '//' in line:
            # Find the comment position
            comment_pos = line.find('//')
            # Check if it's inside a string
            before_comment = line[:comment_pos]
            quote_count = before_comment.count('"') - before_comment.count('\\"')
            if quote_count % 2 == 0:  # Not inside a string
                line = line[:comment_pos].rstrip()
                if line.strip():
                    result.append(line)
                continue
        
        # Keep non-comment lines
        if line.strip() or (len(result) > 0 and result[-1].strip()):
            result.append(line)
    
    # Remove trailing empty lines
    while result and not result[-1].strip():
        result.pop()
    
    return '\n'.join(result)

def process_java_files(root_dir):
    count = 0
    for root, dirs, files in os.walk(root_dir):
        for file in files:
            if file.endswith('.java'):
                filepath = os.path.join(root, file)
                print(f"Processing: {filepath}")
                
                try:
                    with open(filepath, 'r', encoding='utf-8') as f:
                        content = f.read()
                    
                    cleaned_content = remove_comments_from_java(content)
                    
                    with open(filepath, 'w', encoding='utf-8') as f:
                        f.write(cleaned_content)
                    
                    count += 1
                    print(f"  ✓ Cleaned")
                except Exception as e:
                    print(f"  ✗ Error: {e}")
    
    print(f"\nTotal files processed: {count}")

if __name__ == "__main__":
    src_dir = r"C:\Users\aluno\Desktop\WorkItBr-PI-\Telas_Final\src"
    process_java_files(src_dir)
    print("\nAll done!")
